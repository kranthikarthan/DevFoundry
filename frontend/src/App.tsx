import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { ThemeProvider, createTheme } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import { Container } from '@mui/material';

import Header from './components/Header';
import HomePage from './components/HomePage';
import TemplatesPage from './components/TemplatesPage';
import DocumentationPage from './components/DocumentationPage';
import PerformancePage from './components/PerformancePage';
import MetricsPage from './components/MetricsPage';

const theme = createTheme({
  palette: {
    mode: 'light',
    primary: {
      main: '#1976d2',
    },
    secondary: {
      main: '#dc004e',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h4: {
      fontWeight: 600,
    },
    h5: {
      fontWeight: 600,
    },
    h6: {
      fontWeight: 600,
    },
  },
});

function App() {
  return (
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Router>
        <Header />
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/templates" element={<TemplatesPage />} />
            <Route path="/templates/:type" element={<TemplatesPage />} />
            <Route path="/templates/:type/:id" element={<TemplatesPage />} />
            <Route path="/docs" element={<DocumentationPage />} />
            <Route path="/docs/:type" element={<DocumentationPage />} />
            <Route path="/performance" element={<PerformancePage />} />
            <Route path="/performance/:type" element={<PerformancePage />} />
            <Route path="/metrics" element={<MetricsPage />} />
          </Routes>
        </Container>
      </Router>
    </ThemeProvider>
  );
}

export default App;