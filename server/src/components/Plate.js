import React, { Component } from 'react'
import axios from 'axios'

export class Plate extends Component {
    constructor(props) {
        super(props)
        this.state = {plate: []}
    }
    componentDidMount() {
        this.getPlate()
    }
    getPlate() {
        // Get ID Parameter
        var id = this.props.id.match.params.id
        // Call API with ID
        axios.get('http://localhost:9300/plate/' + id)
            //.then(response => this.setState({ plate: response.data }))
            .then(response => console.log(response.data))
    }

    render() {
        return (
            <div>

            </div>
        )
    }
}

export default Plate