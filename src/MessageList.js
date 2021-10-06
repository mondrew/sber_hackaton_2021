import React, {Component} from "react";
import {Button, ListGroup} from "react-bootstrap";
import axios from "axios";

class MessageList extends Component {
    constructor(props) {
        super(props);

        this.state = {
            messages: [],
            userName: "Sasha"
        }
    }

    componentDidMount() {
           axios.get("http://localhost:8080/messages/" + this.state.userName + "/Andrew")
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
            <div>
                {
                    this.state.messages.map(
                        message =>
                            <p>{message.message}</p>
                    )
                }
            </div>
        )
    }
}

export default MessageList;