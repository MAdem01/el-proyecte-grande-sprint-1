export const weekday = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
export const month = [
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
];

export function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const monthName = month[date.getMonth()];
    const day = weekday[date.getDay()];

    return `${day}, ${monthName} ${date.getDate()}, ${year}`;
}

export function formatTime(dateString) {
    const date = new Date(dateString);
    const hour = date.getHours();
    let minutes = date.getMinutes().toString();
    if (minutes.length === 1) {
        minutes = `0${minutes}`;
    }

    return `${hour}:${minutes}`;
}

export function formatAddress(city, district, postcode, streetName, streetNumber) {
    return `${postcode}, ${city}, ${district}. district, ${streetName} Str. ${streetNumber}`;
}

export function formatDateDefault(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}`;
}