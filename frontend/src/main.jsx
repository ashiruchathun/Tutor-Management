import React, { useEffect, useMemo, useState } from 'react';
import { createRoot } from 'react-dom/client';
import {
  ArrowLeft,
  BookOpen,
  GraduationCap,
  Home,
  LogIn,
  Loader2,
  Mail,
  Pencil,
  Phone,
  Plus,
  Search,
  Trash2,
  UsersRound,
} from 'lucide-react';
import './styles.css';

const API_BASE_URL = 'http://localhost:8080/api/tutors';

const emptyForm = {
  name: '',
  email: '',
  phone: '',
  subject: '',
  qualification: '',
  experienceYears: '',
  hourlyRate: '',
  availability: '',
  tutorType: '',
};

function App() {
  const [path, setPath] = useState(window.location.pathname);

  useEffect(() => {
    const handlePopState = () => setPath(window.location.pathname);
    window.addEventListener('popstate', handlePopState);
    return () => window.removeEventListener('popstate', handlePopState);
  }, []);

  function navigate(nextPath) {
    window.history.pushState({}, '', nextPath);
    setPath(nextPath);
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }

  const adminEditMatch = path.match(/^\/admin\/tutors\/edit\/(\d+)$/);
  const isAdminRoute = path.startsWith('/admin');

  return (
    <main>
      {path !== '/login' && <TopNav navigate={navigate} activePath={path} isAdminRoute={isAdminRoute} />}
      {path === '/login' ? (
        <LoginPage navigate={navigate} />
      ) : path === '/' || path === '/tutors' ? (
        <TutorListPage navigate={navigate} isAdmin={false} />
      ) : path === '/admin/tutors' ? (
        <TutorListPage navigate={navigate} isAdmin />
      ) : path === '/admin/tutors/add' ? (
        <TutorFormPage navigate={navigate} />
      ) : adminEditMatch ? (
        <TutorFormPage tutorId={adminEditMatch[1]} navigate={navigate} />
      ) : (
        <NotFoundPage navigate={navigate} />
      )}
    </main>
  );
}

function TopNav({ navigate, activePath, isAdminRoute }) {
  return (
    <nav className="top-nav">
      <button className="brand" onClick={() => navigate('/tutors')}>
        <GraduationCap size={22} />
        Home Tutor Search
      </button>
      <div className="nav-actions">
        <button className={activePath === '/tutors' || activePath === '/' ? 'active' : ''} onClick={() => navigate('/tutors')}>
          <Home size={17} />
          Tutors
        </button>
        {isAdminRoute ? (
          <>
            <button className={activePath === '/admin/tutors' ? 'active' : ''} onClick={() => navigate('/admin/tutors')}>
              <Pencil size={17} />
              Admin
            </button>
            <button className={activePath === '/admin/tutors/add' ? 'active' : ''} onClick={() => navigate('/admin/tutors/add')}>
              <Plus size={17} />
              Add Tutor
            </button>
            <a className="nav-link-button" href="/logout">Logout</a>
          </>
        ) : (
          <a className="nav-link-button" href="/login">Admin</a>
        )}
      </div>
    </nav>
  );
}

function LoginPage({ navigate }) {
  const params = new URLSearchParams(window.location.search);
  const hasError = params.has('error');
  const loggedOut = params.has('logout');

  return (
    <section className="login-page">
      <div className="login-hero">
        <button className="brand login-brand" onClick={() => navigate('/tutors')}>
          <GraduationCap size={24} />
          Home Tutor Search
        </button>
        <p className="eyebrow">Admin access</p>
        <h1>Sign in to manage tutor profiles.</h1>
        <p className="hero-copy">Use the protected admin area to add new tutors, update profile details, and manage directory records.</p>
      </div>

      <form className="panel login-panel" method="post" action="/login">
        <div className="panel-heading">
          <div>
            <p className="eyebrow">Secure login</p>
            <h2>Admin Login</h2>
          </div>
          <LogIn size={24} />
        </div>

        {hasError && <p className="message error">Invalid username or password.</p>}
        {loggedOut && <p className="message success">You have been logged out.</p>}

        <label>
          Username
          <input name="username" autoComplete="username" required />
        </label>
        <label>
          Password
          <input name="password" type="password" autoComplete="current-password" required />
        </label>

        <button className="submit-button">
          <LogIn size={18} />
          Sign in
        </button>

        <button type="button" className="back-button login-back" onClick={() => navigate('/tutors')}>
          <ArrowLeft size={17} />
          Back to public tutors
        </button>
      </form>
    </section>
  );
}

function TutorListPage({ navigate, isAdmin }) {
  const [tutors, setTutors] = useState([]);
  const [searchType, setSearchType] = useState('all');
  const [searchValue, setSearchValue] = useState('');
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });

  useEffect(() => {
    loadTutors();
  }, []);

  const stats = useMemo(() => {
    const subjects = new Set(tutors.map((tutor) => tutor.subject).filter(Boolean));
    const averageRate = tutors.length
      ? Math.round(tutors.reduce((sum, tutor) => sum + Number(tutor.hourlyRate || 0), 0) / tutors.length)
      : 0;

    return [
      { label: 'Tutors', value: tutors.length, icon: UsersRound },
      { label: 'Subjects', value: subjects.size, icon: BookOpen },
      { label: 'Avg. Rate', value: `Rs. ${averageRate}`, icon: GraduationCap },
    ];
  }, [tutors]);

  async function loadTutors() {
    setLoading(true);
    setSearchType('all');
    setSearchValue('');
    setMessage({ type: '', text: '' });

    try {
      setTutors(await requestJson(API_BASE_URL));
    } catch {
      setMessage({ type: 'error', text: 'Could not load tutors. Check that Spring Boot is running on port 8080.' });
    } finally {
      setLoading(false);
    }
  }

  async function searchTutors() {
    const value = searchValue.trim();

    if (searchType === 'all') {
      await loadTutors();
      return;
    }

    if (!value) {
      setMessage({ type: 'error', text: 'Enter a search value first.' });
      return;
    }

    const endpoints = {
      name: `${API_BASE_URL}/search/name?name=${encodeURIComponent(value)}`,
      subject: `${API_BASE_URL}/search/subject?subject=${encodeURIComponent(value)}`,
      type: `${API_BASE_URL}/search/type?tutorType=${encodeURIComponent(value)}`,
    };

    setLoading(true);
    setMessage({ type: '', text: '' });

    try {
      setTutors(await requestJson(endpoints[searchType]));
    } catch {
      setMessage({ type: 'error', text: 'Search failed. Try again.' });
    } finally {
      setLoading(false);
    }
  }

  async function deleteTutor(id) {
    const confirmed = window.confirm('Delete this tutor?');
    if (!confirmed) {
      return;
    }

    try {
      const response = await fetch(`${API_BASE_URL}/${id}`, { method: 'DELETE' });
      if (!response.ok) {
        throw new Error('Delete failed');
      }
      setTutors((current) => current.filter((tutor) => tutor.tutorId !== id));
      setMessage({ type: 'success', text: 'Tutor deleted.' });
    } catch {
      setMessage({ type: 'error', text: 'Could not delete tutor.' });
    }
  }

  return (
    <>
      <section className="hero">
        <div>
          <p className="eyebrow">{isAdmin ? 'Admin dashboard' : 'Tutor directory'}</p>
          <h1>{isAdmin ? 'Manage tutor profiles.' : 'Find every tutor profile.'}</h1>
          <p className="hero-copy">
            {isAdmin
              ? 'Add new tutors, update profile details, and remove tutor records from the protected admin area.'
              : 'Search the directory and review tutor details. Editing is available only from the admin area.'}
          </p>
        </div>
      </section>

      <section className="stats-grid" aria-label="Tutor summary">
        {stats.map((stat) => {
          const Icon = stat.icon;
          return (
            <article className="stat-card" key={stat.label}>
              <Icon size={22} />
              <span>{stat.label}</span>
              <strong>{stat.value}</strong>
            </article>
          );
        })}
      </section>

      <section className="panel directory page-panel">
        <div className="panel-heading">
          <div>
            <p className="eyebrow">Directory</p>
            <h2>Tutors</h2>
          </div>
          {isAdmin && (
            <button className="primary-action compact" onClick={() => navigate('/admin/tutors/add')}>
              <Plus size={17} />
              Add Tutor
            </button>
          )}
        </div>

        <div className="toolbar">
          <select value={searchType} onChange={(event) => setSearchType(event.target.value)}>
            <option value="all">All tutors</option>
            <option value="name">Name</option>
            <option value="subject">Subject</option>
            <option value="type">Tutor type</option>
          </select>
          <div className="search-field">
            <Search size={18} />
            <input value={searchValue} onChange={(event) => setSearchValue(event.target.value)} placeholder="Search tutors" />
          </div>
          <button className="toolbar-button" onClick={searchTutors}>
            <Search size={17} />
            Search
          </button>
          <button className="toolbar-button ghost" onClick={loadTutors}>
            Reset
          </button>
        </div>

        {message.text && <p className={`message ${message.type}`}>{message.text}</p>}

        {loading ? (
          <div className="empty-state">
            <Loader2 className="spin" size={26} />
            Loading tutors
          </div>
        ) : tutors.length === 0 ? (
          <div className="empty-state">No tutor profiles found.</div>
        ) : (
          <div className="tutor-grid">
            {tutors.map((tutor) => (
              <TutorCard key={tutor.tutorId} tutor={tutor} navigate={navigate} onDelete={deleteTutor} isAdmin={isAdmin} />
            ))}
          </div>
        )}
      </section>
    </>
  );
}

function TutorFormPage({ tutorId, navigate }) {
  const [form, setForm] = useState(emptyForm);
  const [loading, setLoading] = useState(Boolean(tutorId));
  const [saving, setSaving] = useState(false);
  const [message, setMessage] = useState({ type: '', text: '' });
  const isEditing = Boolean(tutorId);

  useEffect(() => {
    if (!tutorId) {
      return;
    }

    async function loadTutor() {
      setLoading(true);
      setMessage({ type: '', text: '' });

      try {
        const tutor = await requestJson(`${API_BASE_URL}/${tutorId}`);
        setForm({
          name: tutor.name || '',
          email: tutor.email || '',
          phone: tutor.phone || '',
          subject: tutor.subject || '',
          qualification: tutor.qualification || '',
          experienceYears: tutor.experienceYears ?? '',
          hourlyRate: tutor.hourlyRate ?? '',
          availability: tutor.availability || '',
          tutorType: tutor.tutorType || '',
        });
      } catch {
        setMessage({ type: 'error', text: 'Could not load this tutor profile.' });
      } finally {
        setLoading(false);
      }
    }

    loadTutor();
  }, [tutorId]);

  function updateField(event) {
    const { name, value } = event.target;
    setForm((current) => ({ ...current, [name]: value }));
  }

  async function saveTutor(event) {
    event.preventDefault();
    setSaving(true);
    setMessage({ type: '', text: '' });

    const payload = {
      ...form,
      experienceYears: Number(form.experienceYears),
      hourlyRate: Number(form.hourlyRate),
    };

    try {
      if (isEditing) {
        await requestJson(`${API_BASE_URL}/${tutorId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload),
        });
      } else {
        await requestJson(API_BASE_URL, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(payload),
        });
      }

      navigate('/admin/tutors');
    } catch {
      setMessage({ type: 'error', text: 'Could not save tutor. Please check the form and try again.' });
    } finally {
      setSaving(false);
    }
  }

  return (
    <section className="form-page">
      <button className="back-button" onClick={() => navigate('/admin/tutors')}>
        <ArrowLeft size={17} />
        Back to admin
      </button>

      <form className="panel tutor-form standalone-form" onSubmit={saveTutor}>
        <div className="panel-heading">
          <div>
            <p className="eyebrow">{isEditing ? 'Edit profile' : 'New profile'}</p>
            <h2>{isEditing ? 'Update tutor' : 'Add tutor'}</h2>
          </div>
        </div>

        {message.text && <p className={`message ${message.type}`}>{message.text}</p>}

        {loading ? (
          <div className="empty-state">
            <Loader2 className="spin" size={26} />
            Loading tutor
          </div>
        ) : (
          <>
            <TutorFormFields form={form} updateField={updateField} />
            <button className="submit-button" disabled={saving}>
              {saving ? <Loader2 className="spin" size={18} /> : <Plus size={18} />}
              {isEditing ? 'Save changes' : 'Create tutor'}
            </button>
          </>
        )}
      </form>
    </section>
  );
}

function TutorFormFields({ form, updateField }) {
  return (
    <div className="form-grid two-column">
      <label>
        Name
        <input name="name" value={form.name} onChange={updateField} required />
      </label>
      <label>
        Email
        <input name="email" type="email" value={form.email} onChange={updateField} required />
      </label>
      <label>
        Phone
        <input name="phone" value={form.phone} onChange={updateField} required />
      </label>
      <label>
        Subject
        <input name="subject" value={form.subject} onChange={updateField} required />
      </label>
      <label>
        Qualification
        <input name="qualification" value={form.qualification} onChange={updateField} required />
      </label>
      <label>
        Experience years
        <input name="experienceYears" type="number" min="0" value={form.experienceYears} onChange={updateField} required />
      </label>
      <label>
        Hourly rate
        <input name="hourlyRate" type="number" min="0" step="0.01" value={form.hourlyRate} onChange={updateField} required />
      </label>
      <label>
        Availability
        <select name="availability" value={form.availability} onChange={updateField} required>
          <option value="">Select availability</option>
          <option value="Weekdays">Weekdays</option>
          <option value="Weekends">Weekends</option>
          <option value="Both">Both</option>
        </select>
      </label>
      <label>
        Tutor type
        <select name="tutorType" value={form.tutorType} onChange={updateField} required>
          <option value="">Select type</option>
          <option value="Online">Online</option>
          <option value="Home Visit">Home Visit</option>
          <option value="Hybrid">Hybrid</option>
        </select>
      </label>
    </div>
  );
}

function TutorCard({ tutor, navigate, onDelete, isAdmin }) {
  return (
    <article className="tutor-card">
      <div className="card-top">
        <div className="avatar">{initials(tutor.name)}</div>
        <div>
          <h3>{tutor.name}</h3>
          <span>{tutor.subject}</span>
        </div>
      </div>

      <div className="meta-list">
        <span><Mail size={15} />{tutor.email}</span>
        <span><Phone size={15} />{tutor.phone}</span>
        <span><GraduationCap size={15} />{tutor.qualification}</span>
      </div>

      <div className="chips">
        <span>{tutor.experienceYears} years</span>
        <span>Rs. {tutor.hourlyRate}</span>
        <span>{tutor.availability}</span>
        <span>{tutor.tutorType}</span>
      </div>

      {isAdmin && (
        <div className="card-actions">
          <button onClick={() => navigate(`/admin/tutors/edit/${tutor.tutorId}`)}>
            <Pencil size={16} />
            Edit
          </button>
          <button className="danger" onClick={() => onDelete(tutor.tutorId)}>
            <Trash2 size={16} />
            Delete
          </button>
        </div>
      )}
    </article>
  );
}

function NotFoundPage({ navigate }) {
  return (
    <section className="panel page-panel">
      <p className="eyebrow">Not found</p>
      <h2>That page does not exist.</h2>
      <button className="primary-action compact" onClick={() => navigate('/tutors')}>
        <Home size={17} />
        Back to tutors
      </button>
    </section>
  );
}

async function requestJson(url, options) {
  const response = await fetch(url, {
    ...options,
    headers: {
      Accept: 'application/json',
      ...options?.headers,
    },
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}

function initials(name = '') {
  return name
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((part) => part[0].toUpperCase())
    .join('') || 'T';
}

createRoot(document.getElementById('root')).render(<App />);
