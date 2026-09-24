import './App.css'
import EmployeeComponent from './components/EmployeeComponent.jsx'
import FooterComponent from './components/FooterComponent.jsx'
import HeaderComponent from './components/HeaderComponent.jsx'
import ListEmployeeComponent from './components/ListEmployeeComponent.jsx'
import LoginComponent from './components/LoginComponent.jsx'
import RegisterComponent from './components/RegisterComponent.jsx'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
function App() {

  return (
    <>
    <BrowserRouter>
      <HeaderComponent />
      <Routes>
        {/* //http://localhost:3000 */}
        <Route path='/' element = {<ListEmployeeComponent/>}></Route>

        {/* //http://localhost:3000/employees */}
        <Route path='/employees' element = {<ListEmployeeComponent/>}></Route>
        

        {/* //http://localhost:3000/add-employee */}
        <Route path = '/add-employee' element = {<EmployeeComponent/>}></Route>

        {/* //http://localhost:3000/edit-employee/1 */}
         <Route path = '/edit-employee/:id' element = {<EmployeeComponent/>}></Route>

        {/* //http://localhost:3000/login */}
        <Route path = '/login' element = {<LoginComponent/>}></Route>

        {/* //http://localhost:3000/register */}
        <Route path = '/register' element = {<RegisterComponent/>}></Route>
      </Routes>

      <FooterComponent />
    </BrowserRouter>
      
    </>
  )
}

export default App