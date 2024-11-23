import {Link, useLocation} from "react-router-dom";
import AuditProgress from "../AuditProgress/AuditProgress.jsx";

export function Sidebar(){
    const location = useLocation();

    return (
        <nav className="fw-64 h-[calc(100vh-4rem)] bg-gray-200 p-4 shadow-md fixed">
            <div className="flex flex-col gap-2">
                <Link to="/"
                      className="block p-1 mt-1 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">Home</Link>
                {/*<Link to="/dashboard">Dashboard</Link>*/}
                {/*<Link to="/new-audit">new audit</Link>*/}
                {/*<Link to="/perform-audit">perform audit</Link>*/}
                <Link to="/evaluation"
                      className="block p-1 mt-1 text-gray-700 hover:text-black hover:scale-105 transition-transform transform border border-gray-400 rounded text-center">Evaluation</Link>
            </div>
            <hr className="border-t border-gray-300 my-4"/>
            {location.pathname.startsWith("/perform-audit") && (
                <div className="mt-auto">
                    <AuditProgress/>
                </div>
            )}
        </nav>
    )
}