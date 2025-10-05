import { describe, it, expect, vi } from 'vitest';
import { IntegrationClient } from '../src/index';

describe('IntegrationClient', () => {
  it('lists integrations', async () => {
    const mockData = { integrations: [{ id: 'x', name: 'X', category: 'misc' }] };
    // @ts-expect-error override fetch for test
    global.fetch = vi.fn().mockResolvedValue({ ok: true, json: async () => mockData });

    const client = new IntegrationClient('http://example.test');
    const data = await client.listIntegrations();
    expect(data.integrations[0].id).toBe('x');
  });
});
