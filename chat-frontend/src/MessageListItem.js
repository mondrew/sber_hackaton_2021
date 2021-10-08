import React, {Component} from "react";
import {Card, ListGroup} from "react-bootstrap";

class MessageListItem extends Component {
    constructor(props) {
        super(props);
        }

    render() {
        return (
            <ListGroup.Item
                className={`d-flex ${this.props.currentUser ? 'justify-content-end' : ''}`}
            >
                <Card
                    bg={`${this.props.currentUser ? 'primary' : 'secondary'}`}
                    text='light'
                    style={{ width: '55%' }}
                >
                    <Card.Header className='d-flex justify-content-between align-items-center'>
                        <Card.Text className='small'>{this.props.dateTime}</Card.Text>
                        <Card.Text>{this.props.senderName}</Card.Text>
                    </Card.Header>
                    <Card.Body className='d-flex justify-content-between align-items-center'>
                        <Card.Text>{this.props.messageText}</Card.Text>
                    </Card.Body>
                </Card>
            </ListGroup.Item>
    )
    }
}

export default MessageListItem;