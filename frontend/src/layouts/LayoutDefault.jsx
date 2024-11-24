import {Sidebar} from "../components/Layout/Sidebar.jsx";
import {Header} from "../components/Layout/Header.jsx";
import {Children} from "react";

export function LayoutDefault({children}){
    return (
        <div>
            {/*<Header />*/}
            <div className="grid grid-cols-12 h-screen w-screen">
                <aside className="col-span-2">
                    <Sidebar />
                </aside>
                <main className="col-span-10 h-full">
                    {Children.map(children, child => child)}
                </main>
            </div>
        </div>

    )
}