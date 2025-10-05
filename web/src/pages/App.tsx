import React, { useEffect, useState } from 'react';

type Integration = { id: string; name: string; category: string };

export function App(): JSX.Element {
  const [integrations, setIntegrations] = useState<Integration[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    fetch('/api/integrations')
      .then(async (r) => {
        if (!r.ok) throw new Error('Failed to load integrations');
        const data = await r.json();
        setIntegrations(data.integrations ?? []);
      })
      .catch((e) => setError(e.message));
  }, []);

  return (
    <div style={{ fontFamily: 'Inter, system-ui, sans-serif', maxWidth: 960, margin: '40px auto', padding: 24 }}>
      <header>
        <h1>Developer Integration Portal</h1>
        <p>Discover, test, and integrate with our platform APIs.</p>
        <a href="/api/docs" target="_blank" rel="noreferrer">OpenAPI Docs</a>
      </header>
      <main>
        <h2>Available Integrations</h2>
        {error && <p style={{ color: 'crimson' }}>{error}</p>}
        <ul>
          {integrations.map((i) => (
            <li key={i.id}>
              <strong>{i.name}</strong> — {i.category}
            </li>
          ))}
        </ul>
      </main>
    </div>
  );
}
