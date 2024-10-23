/**
 *
 * @param title toller title den man nutzen kann
 * @param content super cooler content
 * @returns {JSX.Element}
 * @constructor
 */
export function MyExampleComponent({title, content}) {
    return (
        <div>
            <div className="bg-blue-200 text-lg">{title}</div>
            <div className="bg-blue-200">{content}</div>
        </div>
    )
}