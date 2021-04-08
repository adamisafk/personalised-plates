import React, { Component } from 'react'
import { Button, Col, Form, Row } from 'react-bootstrap'

export class PlateForm extends Component {
    constructor(props) {
        super(props)
        this.state = {
            value: ''
        }
        this.handleChange = this.handleChange.bind(this)
    }
    componentDidMount() {
        this.setState({ value: this.props.searchText })
    }
    handleChange(e) {
        this.setState({ value: e.target.value})
    }

    render() {
        return (
            <div style={{paddingBottom: '50px'}}>
            <Form>
                <Row>
                    <Col xs={8}>
                        <Form.Group controlId="formRegSearch.TextInput">
                            <Form.Control value={this.state.value} onChange={this.handleChange} size="lg" type="text" name="search" placeholder="SEARCH" />
                        </Form.Group>
                    </Col>
                    <Col>
                        <Form.Group controlId="formRegSearch.StyleInput">
                            <Form.Control size="lg" name="style" as="select">
                                <option>All Styles</option>
                                <option>AB18 ABC</option>
                                <option>A1 ABC</option>
                                <option>ABC 123</option>
                            </Form.Control>
                        </Form.Group>
                    </Col>
                </Row>

                <Row style={{justifyContent: 'center'}}>
                    <Button variant="success" size="lg" type="submit">Search</Button>
                </Row>
            </Form>
        </div>
        )
    }
}

export default PlateForm
