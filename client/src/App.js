import './App.css';
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import Departments from "./pages/departments/page";
import Statistics from "./pages/statistics/page";
import SignIn from "./pages/signin/page";
import Signup from "./pages/signup/page";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/departments" element={<Departments/>}/>
                <Route path="/signin" element={<SignIn/>}/>
                <Route path="/" element={<Signup/>}/>
                <Route path="/statistics/:departmentId" element={<Statistics/>}/>
            </Routes>
        </Router>
    );
}

export default App;
