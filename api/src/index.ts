import express, { Express } from 'express';
import cors from 'cors';
import helmet from 'helmet';
import morgan from 'morgan';
import swaggerUi from 'swagger-ui-express';
import fs from 'node:fs';
import path from 'node:path';
import { parse } from 'yaml';

export function createApp(): Express {
  const app = express();
  app.use(helmet());
  app.use(cors());
  app.use(express.json());
  app.use(morgan('dev'));

  app.get('/health', (_req, res) => {
    res.json({ status: 'ok' });
  });

  app.get('/integrations', (_req, res) => {
    res.json({
      integrations: [
        { id: 'slack', name: 'Slack', category: 'communications' },
        { id: 'stripe', name: 'Stripe', category: 'payments' },
        { id: 'github', name: 'GitHub', category: 'developer-tools' }
      ]
    });
  });

  const openapiPath = path.join(__dirname, '..', 'openapi', 'openapi.yaml');
  try {
    const openapiYaml = fs.readFileSync(openapiPath, 'utf8');
    const openapiDoc = parse(openapiYaml);
    app.use('/docs', swaggerUi.serve, swaggerUi.setup(openapiDoc));
  } catch {
    // If the OpenAPI file is missing in dev, keep the server running
    // so other routes still work.
  }

  return app;
}

const app = createApp();

if (require.main === module) {
  const port = Number(process.env.PORT) || 3000;
  app.listen(port, () => {
    // eslint-disable-next-line no-console
    console.log(`API listening on http://localhost:${port}`);
  });
}
