import axios from 'axios'

const BASE_URL = "http://localhost:9300"

class AuthService {
    login(email, password) {
        return axios
            .post(BASE_URL + "/login", {
                email,
                password
            })
            .then(response => {
                if(response.data.Authorization) {
                    localStorage.setItem("token", JSON.stringify(response.data.Authorization))
                }
                return response.data
            })
    }
    logout() {
        localStorage.removeItem("token")
    }
    register(firstName, lastName, email, password) {
        return axios
            .post(BASE_URL + "/customer/register", {
                firstName,
                lastName,
                email,
                password
            })
    }
    getToken() {
        return JSON.parse(localStorage.getItem('token'))
    }
}

export default new AuthService()