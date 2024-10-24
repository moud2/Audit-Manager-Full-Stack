import {Link} from "react-router-dom";

export function DashboardSidebar(){
    return (
        <nav className="flex flex-col gap-2 bg-gray-200 h-full w-full">
            <Link to="/">Home</Link>
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/new-audit">new audit</Link>
            <Link to="/perform-audit">perform audit</Link>
            <Link to="/evaluation">evaluation</Link>
        </nav>
    )
}