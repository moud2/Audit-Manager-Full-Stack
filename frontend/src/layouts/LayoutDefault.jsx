import {DashboardSidebar} from "../components/Layout/DashboardSidebar.jsx";
import {Children} from "react";

export function LayoutDefault({children}){
    return (
        <div className="grid grid-cols-12 h-screen w-screen">
            <aside className="col-span-2">
                <DashboardSidebar></DashboardSidebar>
            </aside>
            <main className="col-span-10 h-full mb-24">
                {Children.map(children, child => child)}
            </main>
        </div>
    )
}