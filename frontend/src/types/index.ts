export interface IntegrationTemplate {
  id: string;
  name: string;
  description: string;
  type: IntegrationType;
  category: string;
  language: string;
  framework: string;
  version: string;
  author: string;
  createdAt: string;
  updatedAt: string;
  tags: string[];
  metadata: TemplateMetadata;
  codeTemplate: string;
  configurationExample: string;
  usageExample: string;
  performanceMetrics: PerformanceMetrics;
  reliabilityMetrics: ReliabilityMetrics;
}

export interface TemplateMetadata {
  difficulty: 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED';
  estimatedSetupTime: number;
  prerequisites: string;
  requiredDependencies: string[];
  optionalDependencies: string[];
  documentationUrl: string;
  sourceUrl: string;
  isProductionReady: boolean;
  license: string;
}

export interface PerformanceMetrics {
  averageResponseTime: number;
  throughput: number;
  memoryUsage: number;
  cpuUsage: number;
  concurrentUsers: number;
  performanceGrade: string;
  lastBenchmarked: string;
}

export interface ReliabilityMetrics {
  uptime: number;
  availability: number;
  errorRate: number;
  meanTimeBetweenFailures: number;
  meanTimeToRecovery: number;
  reliabilityGrade: string;
  lastIncident: string;
  monitoringUrl: string;
}

export enum IntegrationType {
  REST = 'REST',
  KAFKA = 'KAFKA',
  SOAP = 'SOAP',
  DATABASE = 'DATABASE',
  FILE = 'FILE',
  CLOUD = 'CLOUD',
  MESSAGE_QUEUE = 'MESSAGE_QUEUE',
  EMAIL = 'EMAIL',
  WEBHOOK = 'WEBHOOK',
  STREAMING = 'STREAMING'
}

export interface Documentation {
  id: string;
  title: string;
  description: string;
  integrationType: string;
  category: string;
  version: string;
  author: string;
  createdAt: string;
  updatedAt: string;
  tags: string[];
  content: string;
  contentType: 'MARKDOWN' | 'HTML' | 'PLAIN_TEXT';
  sections: DocumentationSection[];
  metadata: DocumentationMetadata;
}

export interface DocumentationSection {
  id: string;
  title: string;
  content: string;
  order: number;
  level: string;
  codeExamples: string[];
  relatedLinks: string[];
}

export interface DocumentationMetadata {
  difficulty: 'BEGINNER' | 'INTERMEDIATE' | 'ADVANCED';
  estimatedReadTime: number;
  prerequisites: string[];
  relatedTopics: string[];
  sourceUrl: string;
  isOfficial: boolean;
  license: string;
  lastReviewed: string;
  nextReviewDate: string;
}

export interface PerformanceReport {
  id: string;
  integrationType: string;
  integrationName: string;
  reportDate: string;
  environment: string;
  overall: PerformanceMetrics;
  benchmarks: PerformanceMetrics[];
  issues: PerformanceIssue[];
  recommendations: PerformanceRecommendations;
}

export interface PerformanceIssue {
  id: string;
  severity: 'CRITICAL' | 'HIGH' | 'MEDIUM' | 'LOW';
  category: 'MEMORY' | 'CPU' | 'NETWORK' | 'DATABASE' | 'CODE';
  description: string;
  impact: string;
  solution: string;
  isResolved: boolean;
  detectedAt: string;
  resolvedAt?: string;
}

export interface PerformanceRecommendations {
  overallGrade: string;
  immediateActions: string[];
  shortTermImprovements: string[];
  longTermOptimizations: string[];
  nextReviewDate: string;
  monitoringStrategy: string;
}