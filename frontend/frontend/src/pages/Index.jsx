import {MyExampleComponent} from "../components/MyExampleComponent/MyExampleComponent.jsx";
import {LayoutDefault} from "../layouts/LayoutDefault.jsx";


export function Index() {
    return (
        <LayoutDefault>
            <div className="bg-red-200 w-full h-full">
                I am the index page
                <div className="flex flex-col gap-2">
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                    <MyExampleComponent title="Test Title" content="content"></MyExampleComponent>
                </div>
            </div>
        </LayoutDefault>
    )
}