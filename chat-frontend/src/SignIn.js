import React, {Component} from "react";
import {Button, Col, Container, Form, Row} from "react-bootstrap";
import axios from "axios";

const SERVER_URL = "http://localhost:8080/message"

class SignIn extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userName: localStorage.getItem("userName"),
            publicKey: localStorage.getItem("publicKey"),
            privateKey: localStorage.getItem("privateKey")
        }

        this.handleUserNameChange = this.handleUserNameChange.bind(this);
        this.handlePublicKeyChange = this.handlePublicKeyChange.bind(this);
        this.handlePrivateKeyChange = this.handlePrivateKeyChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUserNameChange(e) {
        this.setState({
            userName: e.target.value
        })
    }

    handlePublicKeyChange(e) {
        this.setState({
            publicKey: e.target.value
        })
    }

    handlePrivateKeyChange(e) {
        this.setState({
            privateKey: e.target.value
        })
    }

    handleSubmit(e) {
        localStorage.setItem("userName", this.state.userName)
        localStorage.setItem("publicKey", this.state.publicKey)
        localStorage.setItem("privateKey", this.state.privateKey)
    }

    checkAuth() {
        return localStorage.getItem("userName") != null &&
            localStorage.getItem("publicKey") != null &&
            localStorage.getItem("privateKey") != null;
    }

    render() {
        if (this.checkAuth()) {
            return (
                <Row>
                    <p>Ваш логин {this.state.userName}, ваш публичный ключ {this.state.publicKey}</p>
                </Row>
            )
        } else {
            return(
                <Form onSubmit={this.handleSubmit}>
                    <Form.Control
                        type="text"
                        placeholder="Введите уникальный логин"
                        value={this.state.userName}
                        onChange={this.handleUserNameChange}
                    />
                    <Form.Control
                        type="text"
                        placeholder="Введите свой публичный ключ"
                        value={this.state.publicKey}
                        onChange={this.handlePublicKeyChange}
                    />
                    <Form.Control
                        type="text"
                        placeholder="Введите свой приватный ключ"
                        value={this.state.privateKey}
                        onChange={this.handlePrivateKeyChange}
                    />
                    <Button type={"submit"}>Подтвердить</Button>
                </Form>
            )
        }
    }
}

export default SignIn;