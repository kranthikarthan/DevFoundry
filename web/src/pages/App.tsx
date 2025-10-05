import React, { useEffect, useState } from 'react';
import { IntegrationClient, type IntegrationSummary } from '@devportal/sdk';

export function App(): JSX.Element {
  const [integrations, setIntegrations] = useState<IntegrationSummary[]>([]);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const client = new IntegrationClient('/api');
    client
      .listIntegrations()
      .then((data) => setIntegrations(data.integrations ?? []))
      .catch((e: unknown) => {
        const message = e instanceof Error ? e.message : 'Unknown error';
        setError(message);
      });
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
