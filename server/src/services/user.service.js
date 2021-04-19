import axios from 'axios'
import authHeader from './auth-header'

const BASE_URL = "http://localhost:9300"

class UserService {
    // Gets user details from auth token
    getUser() {
        return axios.get(BASE_URL + "/customer/get", {headers: authHeader()})
    }
    // Gets all orders by user identified by authorization token
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
    // Refunds ordered plate
    createRefundOrder(oId, pId) {
        const data = {
            orderId: oId,
            plateId: pId
        }
        return axios.post(BASE_URL + "/order/create/refund", data, {headers: authHeader()})
    }
    // Creates a new tranfer order for another user
    createTransferOrder(email, oId) {
        const data = {
            email: email,
            orderId: oId
        }
        return axios.post(BASE_URL + "/order/create/transfer", data, {headers: authHeader()})
    }
    // Creates new sale order
    createSellOrder(orderId, pId, priceStr) {
        const data = {
            prevOrderId: orderId,
            plateId: pId,
            price: priceStr
        }
        return axios.post(BASE_URL + "/order/create/sale", data, {headers: authHeader()})
    }
    // Changes price of plate on sale
    changePlatePrice(pId, priceStr) {
        const data = {
            plateId: pId,
            price: priceStr
        }
        return axios.post(BASE_URL + "/plate/edit/price", data, {headers: authHeader()})
    }
    // Cancel Sale Order
    cancelSaleOrder(oId) {
        const data = {
            orderId: oId
        }
        return axios.post(BASE_URL + "/order/delete", data, {headers: authHeader()})
    }
}

export default new UserService()