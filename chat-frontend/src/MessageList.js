import React, {Component} from "react";
import {ListGroup} from "react-bootstrap";
import ImageListItem from "./MessageListItem"
import axios from "axios";

const listStyles = {
    height: '80vh',
    border: '1px solid rgba(0,0,0,.4)',
    borderRadius: '4px',
    overflow: 'auto'
}

class MessageList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            messages: [],
            publicKey: localStorage.getItem("publicKey"),
            userName: localStorage.getItem("userName"),
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
            <ListGroup variant='flush' style={listStyles}>
                {this.state.messages.map((message) => (
                            <ImageListItem
                                currentUser={message.senderPublicKey === this.state.publicKey}
                                senderName={message.senderPublicKey}
                                messageText={message.message}
                                dateTime={message.timestamp}
                            />
                ))}
            </ListGroup>
        )
    }
}

export default MessageList;