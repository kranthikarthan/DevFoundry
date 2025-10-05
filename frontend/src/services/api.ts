import axios from 'axios';
import {
  IntegrationTemplate,
  IntegrationType,
  Documentation,
  PerformanceReport
} from '../types';

const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';

// Create axios instance with default config
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Integration Service API
export const integrationApi = {
  getAllTemplates: (): Promise<IntegrationTemplate[]> =>
    api.get('/integrations').then(res => res.data),

  getTemplatesByType: (type: IntegrationType): Promise<IntegrationTemplate[]> =>
    api.get(`/integrations/${type}`).then(res => res.data),

  getTemplateByTypeAndId: (type: IntegrationType, id: string): Promise<IntegrationTemplate> =>
    api.get(`/integrations/${type}/${id}`).then(res => res.data),

  getIntegrationTypes: (): Promise<IntegrationType[]> =>
    api.get('/integrations/types').then(res => res.data),
};

// Documentation Service API
export const documentationApi = {
  getAllDocumentation: (): Promise<Documentation[]> =>
    api.get('/docs').then(res => res.data),

  getDocumentationByType: (type: string): Promise<Documentation> =>
    api.get(`/docs/${type}`).then(res => res.data),

  getDocumentationTypes: (): Promise<string[]> =>
    api.get('/docs/types').then(res => res.data),
};

// Performance Service API
export const performanceApi = {
  getAllPerformanceReports: (): Promise<PerformanceReport[]> =>
    api.get('/performance').then(res => res.data),

  getPerformanceReportByType: (type: string): Promise<PerformanceReport> =>
    api.get(`/performance/${type}`).then(res => res.data),

  getPerformanceTypes: (): Promise<string[]> =>
    api.get('/performance/types').then(res => res.data),
};

// Metrics Service API
export const metricsApi = {
  simulateIntegrationRequest: (): Promise<any> =>
    api.get('/metrics/simulate').then(res => res.data),

  simulateTemplateDownload: (): Promise<any> =>
    api.get('/metrics/simulate-download').then(res => res.data),

  simulateDocumentationView: (): Promise<any> =>
    api.get('/metrics/simulate-docs').then(res => res.data),

  getHealth: (): Promise<any> =>
    api.get('/metrics/health').then(res => res.data),
};

export default api;