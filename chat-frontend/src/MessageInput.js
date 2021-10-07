import React, {Component} from "react";
import axios from "axios";
import {Form, Button, Row, Col} from "react-bootstrap";

const SERVER_URL = "http://localhost:8080/message"

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
        let newMessage = {
            sender: this.state.publicKey,
            recipient: this.props.recipientKey,
            senderName: this.state.userName,
            recipientName: this.props.recipientName,
            message: this.state.message,
            timestamp: Date.now()
        }

        axios.post(SERVER_URL, newMessage)
            .then(() => this.setState({message: ""}))
            .catch((error)=>console.log(error));
    }

    render() {
        return(
            <Form onSubmit={this.handleSubmit}>
                <Row fluid>
                    <Col xs={8}>
                        <Form.Control
                            type="text"
                            placeholder="Введите сообщение"
                            value={this.state.message}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={4}>
                        <Button type="submit">Отправить</Button>
                    </Col>
                </Row>
            </Form>
        )
    }
}

export default MessageInput;