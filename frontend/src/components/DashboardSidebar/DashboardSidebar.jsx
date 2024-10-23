import {Link} from "react-router-dom";

export function DashboardSidebar(){
    return (
        <nav className="flex flex-col gap-2 bg-gray-200 h-full w-full">
            <Link to="/">Home</Link>
            <Link to="/dashboard">Dashboard</Link>
            <Link to="/my/example/nested/page">/my/example/nested/page</Link>
        </nav>
    )
}