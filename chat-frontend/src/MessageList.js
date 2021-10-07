import React, {Component} from "react";
import {Container, Row} from "react-bootstrap";
import axios from "axios";

class MessageList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            messages: [],
            publicKey: localStorage.getItem("publicKey"),
            userName: localStorage.getItem("userName"),
        }

        // this.getLoginByPublicKey = this.getLoginByPublicKey.bind(this)
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

    // getLoginByPublicKey(key) {
    //     let login
    //     axios.get("http://localhost:8080/user/key/" + key)
    //         .then((res)=> {
    //             login = res.data.login
    //     })
    //     return login
    // }

    render() {
        return (
            <Container>
                {
                    this.state.messages.map(
                        message =>
                            <Row class={message.senderPublicKey === this.state.publicKey ? "text-right" : "text-left"}>{message.senderPublicKey + ": "}{message.message}</Row>
                    )
                }
            </Container>
        )
    }
}

export default MessageList;