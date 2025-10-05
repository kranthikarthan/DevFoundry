import { describe, it, expect } from 'vitest';
import request from 'supertest';
import { createApp } from '../src/index';

const app = createApp();

describe('API', () => {
  it('GET /health returns ok', async () => {
    const res = await request(app).get('/health');
    expect(res.status).toBe(200);
    expect(res.body).toEqual({ status: 'ok' });
  });

  it('GET /integrations returns list', async () => {
    const res = await request(app).get('/integrations');
    expect(res.status).toBe(200);
    expect(Array.isArray(res.body.integrations)).toBe(true);
    expect(res.body.integrations.length).toBeGreaterThan(0);
  });
});
