import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import {
  Typography,
  Box,
  Card,
  CardContent,
  Button,
  Chip,
  Alert,
  Grid,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Accordion,
  AccordionSummary,
  AccordionDetails,
  Rating,
} from '@mui/material';
import {
  ExpandMore,
  Speed,
  Assessment,
  Warning,
  CheckCircle,
  Schedule,
  TrendingUp,
} from '@mui/icons-material';

import { PerformanceReport } from '../types';
import { performanceApi } from '../services/api';

const PerformancePage: React.FC = () => {
  const { type } = useParams<{ type?: string }>();
  const [reports, setReports] = useState<PerformanceReport[]>([]);
  const [selectedReport, setSelectedReport] = useState<PerformanceReport | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadPerformanceReports();
  }, [type]);

  useEffect(() => {
    if (type && reports.length > 0) {
      const report = reports.find(r => r.integrationType === type);
      if (report) {
        setSelectedReport(report);
      }
    }
  }, [type, reports]);

  const loadPerformanceReports = async () => {
    try {
      setLoading(true);
      setError(null);

      const data = await performanceApi.getAllPerformanceReports();
      setReports(data);
    } catch (err) {
      setError('Failed to load performance reports');
      console.error('Error loading performance reports:', err);
    } finally {
      setLoading(false);
    }
  };

  const getGradeColor = (grade: string) => {
    switch (grade.toUpperCase()) {
      case 'A': return 'success';
      case 'B': return 'warning';
      case 'C': return 'error';
      default: return 'default';
    }
  };

  const getSeverityColor = (severity: string) => {
    switch (severity) {
      case 'CRITICAL': return 'error';
      case 'HIGH': return 'warning';
      case 'MEDIUM': return 'info';
      case 'LOW': return 'success';
      default: return 'default';
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Loading performance reports...</Typography>
      </Box>
    );
  }

  if (error) {
    return (
      <Alert severity="error" sx={{ mt: 2 }}>
        {error}
      </Alert>
    );
  }

  if (selectedReport) {
    return (
      <Box>
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to="/performance" variant="outlined">
            ← Back to Performance Reports
          </Button>
        </Box>

        <Typography variant="h4" gutterBottom>
          {selectedReport.integrationName} - Performance Report
        </Typography>

        <Box sx={{ mb: 3 }}>
          <Chip label={selectedReport.integrationType} color="primary" sx={{ mr: 1 }} />
          <Chip label={selectedReport.environment} variant="outlined" sx={{ mr: 1 }} />
          <Chip
            label={`Grade: ${selectedReport.overall.grade}`}
            color={getGradeColor(selectedReport.overall.grade)}
          />
        </Box>

        <Typography variant="body1" color="text.secondary" paragraph>
          Report Date: {new Date(selectedReport.reportDate).toLocaleDateString()}
        </Typography>

        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <Card>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Overall Performance Metrics
                </Typography>
                <List>
                  <ListItem>
                    <ListItemIcon><Speed /></ListItemIcon>
                    <ListItemText
                      primary={`${selectedReport.overall.responseTime}ms`}
                      secondary="Response Time"
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemIcon><TrendingUp /></ListItemIcon>
                    <ListItemText
                      primary={`${selectedReport.overall.throughput} req/sec`}
                      secondary="Throughput"
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemIcon><Assessment /></ListItemIcon>
                    <ListItemText
                      primary={`${selectedReport.overall.memoryUsage}MB`}
                      secondary="Memory Usage"
                    />
                  </ListItem>
                  <ListItem>
                    <ListItemText
                      primary={`${selectedReport.overall.concurrentUsers} users`}
                      secondary="Max Concurrent Users"
                    />
                  </ListItem>
                </List>
              </CardContent>
            </Card>
          </Grid>

          <Grid item xs={12} md={6}>
            <Card>
              <CardContent>
                <Typography variant="h6" gutterBottom>
                  Performance Benchmarks
                </Typography>
                {selectedReport.benchmarks.map((benchmark, index) => (
                  <Box key={index} sx={{ mb: 2, p: 2, bgcolor: 'grey.50', borderRadius: 1 }}>
                    <Typography variant="subtitle2" gutterBottom>
                      {benchmark.testScenario}
                    </Typography>
                    <Typography variant="body2">
                      Response Time: {benchmark.responseTime}ms |
                      Throughput: {benchmark.throughput} req/sec |
                      Grade: {benchmark.grade}
                    </Typography>
                  </Box>
                ))}
              </CardContent>
            </Card>
          </Grid>
        </Grid>

        <Card sx={{ mt: 3 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Issues & Recommendations
            </Typography>

            <Accordion>
              <AccordionSummary expandIcon={<ExpandMore />}>
                <Typography variant="subtitle1">
                  Issues ({selectedReport.issues.length})
                </Typography>
              </AccordionSummary>
              <AccordionDetails>
                {selectedReport.issues.map((issue) => (
                  <Box key={issue.id} sx={{ mb: 2, p: 2, border: 1, borderColor: 'divider', borderRadius: 1 }}>
                    <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                      <Chip
                        label={issue.severity}
                        color={getSeverityColor(issue.severity)}
                        size="small"
                        sx={{ mr: 1 }}
                      />
                      <Typography variant="subtitle2">
                        {issue.category} - {issue.isResolved ? 'Resolved' : 'Open'}
                      </Typography>
                    </Box>
                    <Typography variant="body2" paragraph>
                      {issue.description}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      <strong>Impact:</strong> {issue.impact}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                      <strong>Solution:</strong> {issue.solution}
                    </Typography>
                  </Box>
                ))}
              </AccordionDetails>
            </Accordion>

            <Accordion>
              <AccordionSummary expandIcon={<ExpandMore />}>
                <Typography variant="subtitle1">
                  Recommendations
                </Typography>
              </AccordionSummary>
              <AccordionDetails>
                <Box sx={{ mb: 2 }}>
                  <Typography variant="subtitle2" gutterBottom>
                    Immediate Actions:
                  </Typography>
                  <List dense>
                    {selectedReport.recommendations.immediateActions.map((action, index) => (
                      <ListItem key={index}>
                        <ListItemIcon><Warning color="warning" /></ListItemIcon>
                        <ListItemText primary={action} />
                      </ListItem>
                    ))}
                  </List>
                </Box>

                <Box sx={{ mb: 2 }}>
                  <Typography variant="subtitle2" gutterBottom>
                    Short-term Improvements:
                  </Typography>
                  <List dense>
                    {selectedReport.recommendations.shortTermImprovements.map((improvement, index) => (
                      <ListItem key={index}>
                        <ListItemIcon><Assessment color="info" /></ListItemIcon>
                        <ListItemText primary={improvement} />
                      </ListItem>
                    ))}
                  </List>
                </Box>

                <Box>
                  <Typography variant="subtitle2" gutterBottom>
                    Long-term Optimizations:
                  </Typography>
                  <List dense>
                    {selectedReport.recommendations.longTermOptimizations.map((optimization, index) => (
                      <ListItem key={index}>
                        <ListItemIcon><TrendingUp color="success" /></ListItemIcon>
                        <ListItemText primary={optimization} />
                      </ListItem>
                    ))}
                  </List>
                </Box>
              </AccordionDetails>
            </Accordion>
          </CardContent>
        </Card>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Performance Reports
      </Typography>

      {type && (
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to="/performance" variant="outlined">
            ← Back to All Reports
          </Button>
        </Box>
      )}

      <Grid container spacing={3}>
        {reports.map((report) => (
          <Grid item xs={12} md={6} lg={4} key={report.id}>
            <Card sx={{ height: '100%' }}>
              <CardContent>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
                  <Typography variant="h6">
                    {report.integrationName}
                  </Typography>
                  <Chip
                    label={`Grade: ${report.overall.grade}`}
                    color={getGradeColor(report.overall.grade)}
                    size="small"
                  />
                </Box>

                <Typography variant="body2" color="text.secondary" paragraph>
                  {report.integrationType} Integration
                </Typography>

                <Box sx={{ mb: 2 }}>
                  <Typography variant="body2">
                    Response Time: {report.overall.responseTime}ms
                  </Typography>
                  <Typography variant="body2">
                    Throughput: {report.overall.throughput} req/sec
                  </Typography>
                  <Typography variant="body2">
                    Environment: {report.environment}
                  </Typography>
                </Box>

                <Typography variant="body2" color="text.secondary">
                  Report Date: {new Date(report.reportDate).toLocaleDateString()}
                </Typography>
              </CardContent>

              <CardContent sx={{ pt: 0 }}>
                <Button
                  component={Link}
                  to={`/performance/${report.integrationType}`}
                  variant="outlined"
                  fullWidth
                >
                  View Full Report
                </Button>
              </CardContent>
            </Card>
          </Grid>
        ))}
      </Grid>
    </Box>
  );
};

export default PerformancePage;