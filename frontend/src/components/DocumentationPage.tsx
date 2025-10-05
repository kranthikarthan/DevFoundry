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
  Accordion,
  AccordionSummary,
  AccordionDetails,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
} from '@mui/material';
import { ExpandMore, Description, Schedule, Person } from '@mui/icons-material';
import ReactMarkdown from 'react-markdown';

import { Documentation } from '../types';
import { documentationApi } from '../services/api';

const DocumentationPage: React.FC = () => {
  const { type } = useParams<{ type?: string }>();
  const [documentation, setDocumentation] = useState<Documentation[]>([]);
  const [selectedDoc, setSelectedDoc] = useState<Documentation | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    loadDocumentation();
  }, [type]);

  useEffect(() => {
    if (type && documentation.length > 0) {
      const doc = documentation.find(d => d.integrationType === type);
      if (doc) {
        setSelectedDoc(doc);
      }
    }
  }, [type, documentation]);

  const loadDocumentation = async () => {
    try {
      setLoading(true);
      setError(null);

      const data = await documentationApi.getAllDocumentation();
      setDocumentation(data);
    } catch (err) {
      setError('Failed to load documentation');
      console.error('Error loading documentation:', err);
    } finally {
      setLoading(false);
    }
  };

  const getDifficultyColor = (difficulty: string) => {
    switch (difficulty) {
      case 'BEGINNER': return 'success';
      case 'INTERMEDIATE': return 'warning';
      case 'ADVANCED': return 'error';
      default: return 'default';
    }
  };

  if (loading) {
    return (
      <Box sx={{ display: 'flex', justifyContent: 'center', mt: 4 }}>
        <Typography>Loading documentation...</Typography>
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

  if (selectedDoc) {
    return (
      <Box>
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to="/docs" variant="outlined">
            ← Back to Documentation
          </Button>
        </Box>

        <Typography variant="h4" gutterBottom>
          {selectedDoc.title}
        </Typography>

        <Box sx={{ mb: 3 }}>
          <Chip label={selectedDoc.integrationType} color="primary" sx={{ mr: 1 }} />
          <Chip label={selectedDoc.metadata.difficulty} color={getDifficultyColor(selectedDoc.metadata.difficulty)} sx={{ mr: 1 }} />
          <Chip label={`v${selectedDoc.version}`} variant="outlined" />
        </Box>

        <Typography variant="body1" color="text.secondary" paragraph>
          {selectedDoc.description}
        </Typography>

        <Card sx={{ mb: 3 }}>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Documentation Overview
            </Typography>
            <List>
              <ListItem>
                <ListItemIcon><Person /></ListItemIcon>
                <ListItemText primary={`Author: ${selectedDoc.author}`} />
              </ListItem>
              <ListItem>
                <ListItemIcon><Schedule /></ListItemIcon>
                <ListItemText
                  primary={`Estimated Read Time: ${selectedDoc.metadata.estimatedReadTime} minutes`}
                />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Last Reviewed: ${selectedDoc.metadata.lastReviewed}`} />
              </ListItem>
              <ListItem>
                <ListItemText primary={`Next Review: ${selectedDoc.metadata.nextReviewDate}`} />
              </ListItem>
            </List>
          </CardContent>
        </Card>

        <Card>
          <CardContent>
            <Typography variant="h6" gutterBottom>
              Content
            </Typography>
            {selectedDoc.sections.map((section) => (
              <Accordion key={section.id}>
                <AccordionSummary expandIcon={<ExpandMore />}>
                  <Typography variant="h6">{section.title}</Typography>
                </AccordionSummary>
                <AccordionDetails>
                  <ReactMarkdown>
                    {section.content}
                  </ReactMarkdown>
                </AccordionDetails>
              </Accordion>
            ))}
          </CardContent>
        </Card>
      </Box>
    );
  }

  return (
    <Box>
      <Typography variant="h4" gutterBottom>
        Documentation
      </Typography>

      {type && (
        <Box sx={{ mb: 3 }}>
          <Button component={Link} to="/docs" variant="outlined">
            ← Back to All Documentation
          </Button>
        </Box>
      )}

      <Box>
        {documentation.map((doc) => (
          <Card key={doc.id} sx={{ mb: 3 }}>
            <CardContent>
              <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', mb: 2 }}>
                <Typography variant="h6">
                  {doc.title}
                </Typography>
                <Chip label={doc.integrationType} color="primary" size="small" />
              </Box>

              <Typography variant="body2" color="text.secondary" paragraph>
                {doc.description}
              </Typography>

              <Box sx={{ mb: 2 }}>
                <Chip
                  label={doc.metadata.difficulty}
                  size="small"
                  color={getDifficultyColor(doc.metadata.difficulty)}
                  sx={{ mr: 1 }}
                />
                <Chip
                  label={`${doc.metadata.estimatedReadTime} min read`}
                  size="small"
                  variant="outlined"
                />
              </Box>
            </CardContent>
            <CardContent sx={{ pt: 0 }}>
              <Button
                component={Link}
                to={`/docs/${doc.integrationType}`}
                variant="outlined"
              >
                Read Documentation
              </Button>
            </CardContent>
          </Card>
        ))}
      </Box>
    </Box>
  );
};

export default DocumentationPage;