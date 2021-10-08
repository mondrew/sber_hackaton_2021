import React, {Component} from "react";
import axios from "axios";
import {Form, Button} from "react-bootstrap";

const SERVER_URL = "http://localhost:8080/messages"

class MessageInput extends Component {
    constructor(props) {
        super(props);

        this.state = {
            message: "",
            userName: localStorage.getItem("userName"),
            publicKey: localStorage.getItem("publicKey"),
            privateKey: localStorage.getItem("privateKey")
        }

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(e) {
        this.setState({
            message: e.target.value
        })
    }

    handleSubmit(e) {
        if (localStorage.getItem("publicKey") == null) {
            alert("Войдите в систему")
            return
        }

        let newMessage = {
            senderPublicKey: localStorage.getItem("publicKey"),
            recipientPublicKey: this.props.recipientKey,
            senderLogin: localStorage.getItem("userName"),
            recipientLogin: this.props.recipientName,
            message: this.state.message,
            timestamp: Date.now()
        }

        axios.post(SERVER_URL, newMessage,)
            .then((res)=>this.setState({message: ""}))
            .catch((error)=>alert(error));
    }

    render() {
        return(
            <Form>
                <Form.Group className="d-flex">
                    <Form.Control
                        autoFocus={true}
                        type="text"
                        placeholder="Введите сообщение"
                        value={this.state.message}
                        onChange={this.handleChange}
                    />
                    <Button variant="success" onClick={this.handleSubmit}>Отправить</Button>
                </Form.Group>
            </Form>
        )
    }
}

export default MessageInput;