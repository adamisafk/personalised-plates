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
    createPurchaseOrder(id) {
        const data = {
            plateId: id
        }
        return axios.post(BASE_URL + "/order/create/purchase", data, {headers: authHeader()} )
    }
    createRefundOrder(oId, pId) {
        const data = {
            orderId: oId,
            plateId: pId
        }
        return axios.post(BASE_URL + "/order/create/refund", data, {headers: authHeader()})
    }
    createTransferOrder(email, oId) {
        const data = {
            email: email,
            orderId: oId
        }
        return axios.post(BASE_URL + "/order/create/transfer", data, {headers: authHeader()})
    }
    createSellOrder(orderId, pId, priceStr) {
        const data = {
            prevOrderId: orderId,
            plateId: pId,
            price: priceStr
        }
        return axios.post(BASE_URL + "/order/create/sale", data, {headers: authHeader()})
    }

    // Updates order status if the user sells or transfers their registered plates
    changeOrder() {
        
    }
}

export default new UserService()