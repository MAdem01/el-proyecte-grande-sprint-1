
export default function InputField(props) {
    return (
        <input className={props.className} placeholder={props.placeholder} type={props.type} value={props.value} onChange={props.onChange} />
    )
}