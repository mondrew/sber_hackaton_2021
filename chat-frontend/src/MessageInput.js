import React, {Component} from "react";
import axios from "axios";
import {Form, Button, Row, Col} from "react-bootstrap";

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
        if (this.state.publicKey == null) {
            alert("Войдите в систему")
            return
        }

        let newMessage = {
            senderPublicKey: this.state.publicKey,
            recipientPublicKey: this.props.recipientKey,
            senderLogin: this.state.userName,
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
                <Row>
                    <Col xs={8}>
                        <Form.Control
                            autoFocus={true}
                            type="text"
                            placeholder="Введите сообщение"
                            value={this.state.message}
                            onChange={this.handleChange}
                        />
                    </Col>
                    <Col xs={4}>
                        <Button onClick={this.handleSubmit}>Отправить</Button>
                    </Col>
                </Row>
            </Form>
        )
    }
}

export default MessageInput;