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
           axios.get("http://localhost:8080/messages")
                .then((res) => {
                    if (res) {
                        this.setState({
                            messages: res.data
                        });
                    }
                });
    }

    getLoginByPublicKey(key) {
        let user = null
        axios.get("http://localhost:8080/key/" + key).then((res)=> {
            user = res.data.login
        })
            .catch((error) => console.log(error))
        return user;
    }

    render() {
        return (
            <Container>
                {
                    this.state.messages.map(
                        message =>
                            <Row class={message.senderPublicKey === this.state.publicKey ? "text-right" : "text-left"}>{this.getLoginByPublicKey(message.senderPublicKey) + ": "}{message.message}</Row>
                    )
                }
            </Container>
        )
    }
}

export default MessageList;