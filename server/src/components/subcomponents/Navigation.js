import React from 'react'
import { Navbar, Nav } from 'react-bootstrap'
import { useLocation } from 'react-router-dom'

function Navigation(props) {
    const location = useLocation()

    return (
        <div style={{paddingBottom: '30px'}}>
            <Navbar sticky="top" bg="dark" variant="dark" expand="lg">
                <Navbar.Brand href="/">License Plate Management System</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link className={`${location.pathname === "/" ? "active" : ""}`} href="/">Home</Nav.Link>
                    <Nav.Link className={`${location.pathname === "/about" ? "active" : ""}`} href="/about">About</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link className={`${location.pathname === "/profile" ? "active" : ""}`} href="/profile">My Account</Nav.Link>
                    <Nav.Link className={`${localStorage.getItem("token") === null ? "d-none" : "active"}`} href="/logout">Logout</Nav.Link>
                </Nav>
                </Navbar.Collapse>
            </Navbar>
        </div>
    )
}

export default Navigation
