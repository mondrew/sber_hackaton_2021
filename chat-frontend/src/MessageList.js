import React, {Component} from "react";
import {Container, Row} from "react-bootstrap";
import axios from "axios";

class MessageList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            messages: [],
            publicKey: localStorage.getItem("publicKey"),
            userName: localStorage.getItem("userName")
        }
    }

    componentDidMount() {
           axios.get("http://localhost:8080/messages/" + this.state.publicKey + "/" + this.props.companionKey)
                .then((res) => {
                    if (res) {
                        this.setState({
                            messages: res.data
                        });
                    }
                });
    }

    render() {
        return (
            <Container>
                {
                    this.state.messages.map(
                        message =>
                            <Row class={message.sender === this.state.publicKey ? "text-right" : "text-left"}>{message.sender + ": "}{message.message}</Row>
                    )
                }
            </Container>
        )
    }
}

export default MessageList;