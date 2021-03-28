import React from 'react'
import { Navbar, Nav } from 'react-bootstrap'

function Navigation() {
    return (
        <div style={{paddingBottom: '30px'}}>
            <Navbar sticky="top" bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="/">License Plate Management System</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link href="/">Home</Nav.Link>
                    <Nav.Link href="/about">About</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link href="/profile">Profile</Nav.Link>
                </Nav>
                </Navbar.Collapse>
            </Navbar>
        </div>
    )
}

export default Navigation
