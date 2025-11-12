import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import App from './App.jsx'
import { AlertHelper } from './utilities/AlertHelper.js'
import { AlertProvider,useAlert } from './utilities/AlertProvider.jsx'
import { useEffect } from 'react'


const InitAlert = () => {
  const alert = useAlert()
  useEffect(() => {
    AlertHelper.initialize(alert)
  }, [alert])
  return null
}

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <AlertProvider>
      <InitAlert />
      <App />
    </AlertProvider>
  </StrictMode>,
)
