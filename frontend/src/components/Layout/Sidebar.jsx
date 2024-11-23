import {Link, useLocation} from "react-router-dom";
import AuditProgress from "../AuditProgress/AuditProgress.jsx";

export function Sidebar(){
    const location = useLocation();

    return (
        <nav className="flex flex-col justify-between bg-gray-200 h-full w-full">
            <div className="flex flex-col gap-2">
                <Link to="/">Home</Link>
                <Link to="/dashboard">Dashboard</Link>
                <Link to="/new-audit">new audit</Link>
                <Link to="/perform-audit">perform audit</Link>
                <Link to="/evaluation">evaluation</Link>
            </div>
            {location.pathname.startsWith("/perform-audit") && (
                <div className="mt-auto">
                        <AuditProgress />
                </div>
            )}
        </nav>
    )
}