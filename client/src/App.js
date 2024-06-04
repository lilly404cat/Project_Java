import './App.css';
import {Route, BrowserRouter as Router, Routes,  useLocation } from "react-router-dom";
import Departments from "./pages/departments/page";
import Statistics from "./pages/statistics/page";
import SignIn from "./pages/signin/page";
import Signup from "./pages/signup/page";
import Header from "./pages/header/page";
import Manager from "./pages/manage_supplies/page";


function App() {
    const location = useLocation();
    const pathsWithHeader = ['/departments', '/statistics', '/management']; // List paths where Header should be visible
    const showHeader = pathsWithHeader.some(path => location.pathname.startsWith(path));


    return (
        <>
            {showHeader && <Header />}
            <Routes>
                <Route path="/departments" element={<Departments/>}/>
                <Route path="/signin" element={<SignIn/>}/>
                <Route path="/" element={<Signup/>}/>
                <Route path="/statistics/:departmentId" element={<Statistics/>}/>
                <Route path="/management" element={<Manager />}/>
            </Routes>
        </>
    );
}

function AppWrapper() {
    return (
        <Router>
            <App />
        </Router>
    );
}

export default AppWrapper;
