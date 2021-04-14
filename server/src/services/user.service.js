import axios from 'axios'
import authHeader from './auth-header'

const BASE_URL = "http://localhost:9300"

class UserService {
    // Gets user details from auth token
    getUser() {
        return axios.get(BASE_URL + "/customer/get", {headers: authHeader()})
    }
    getOrders() {
        return axios.get(BASE_URL + "/order/get", {headers: authHeader()})
    }

    // Place an order on a plate
    createOrder(plateId) {
        return axios.post(BASE_URL + "/order/create?plateId=" + plateId, {headers: authHeader()})
    }

    // Updates order status if the user sells or transfers their registered plates
    changeOrder() {
        
    }
}

export default new UserService()