import React, {Component} from "react";
import {Button, Form} from "react-bootstrap";
import axios from "axios";

const SERVER_URL = "http://localhost:8080/users"

class SignIn extends Component {
    constructor(props) {
        super(props);

        this.state = {
            userName: localStorage.getItem("userName"),
            publicKey: localStorage.getItem("publicKey"),
            privateKey: localStorage.getItem("privateKey"),
            phoneNumber: localStorage.getItem("phoneNumber")
        }

        this.handleUserNameChange = this.handleUserNameChange.bind(this);
        this.handlePublicKeyChange = this.handlePublicKeyChange.bind(this);
        this.handlePrivateKeyChange = this.handlePrivateKeyChange.bind(this);
        this.handlePhoneNumberChange = this.handlePhoneNumberChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleUserNameChange(e) {
        this.setState({
            userName: e.target.value
        })
    }

    handlePhoneNumberChange(e) {
        this.setState({
            phoneNumber: e.target.value
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
        let user = {
            publicKey: this.state.publicKey,
            login: this.state.userName,
            phoneNumber: this.state.phoneNumber
        }

        axios.post(SERVER_URL, user).then((res) => {
                if (res.data.login === undefined) {
                    alert("Логин/публичный ключ уже занят");
                } else {
                    localStorage.setItem("userName", this.state.userName)
                    localStorage.setItem("publicKey", this.state.publicKey)
                    localStorage.setItem("privateKey", this.state.privateKey)
                    localStorage.setItem("phoneNumber", this.state.phoneNumber)
                    alert("Вы успешно зарегистрировались и вошли в систему")
                    this.forceUpdate()
                }
            })
            .catch((error)=>alert(error));
    }

    checkAuth() {
        return localStorage.getItem("userName") != null &&
            localStorage.getItem("publicKey") != null &&
            localStorage.getItem("privateKey") != null;
    }

    render() {
        if (this.checkAuth()) {
            return (
                <h3 className='mt-2 text-center'>Ваш логин: {this.state.userName}</h3>
            )
        } else {
            return(
                <Form>
                    <Form.Control
                        type="text"
                        placeholder="Введите уникальный логин"
                        value={this.state.userName == null ? "" : this.state.userName}
                        onChange={this.handleUserNameChange}
                    />
                    <Form.Control
                        type="text"
                        placeholder="Введите свой публичный ключ"
                        value={this.state.publicKey == null ? "" : this.state.publicKey}
                        onChange={this.handlePublicKeyChange}
                    />
                    <Form.Control
                        type="text"
                        placeholder="Введите свой приватный ключ"
                        value={this.state.privateKey == null ? "" : this.state.privateKey}
                        onChange={this.handlePrivateKeyChange}
                    />                    <Form.Control
                        type="text"
                        placeholder="Введите свой номер телефона"
                        value={this.state.phoneNumber == null ? "" : this.state.phoneNumber}
                        onChange={this.handlePhoneNumberChange}
                    />
                    <Button onClick={this.handleSubmit}>Подтвердить</Button>
                </Form>
            )
        }
    }
}

export default SignIn;