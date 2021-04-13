// Helper function to build HTTP request headers requiring authorization
export default function authHeader() {
    const token = JSON.parse(localStorage.getItem('token'))

    if(token) {
        return { Authorization: token }
    } else {
        return {}
    }
}