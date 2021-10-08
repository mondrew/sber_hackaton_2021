import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import MessageList from "./MessageList";
import MessageInput from "./MessageInput";
import SignIn from "./SignIn";
import {Container} from "react-bootstrap";

function App() {
  return (
      <Container style={{ maxWidth: '800px' }}>
          <h1 className='mt-2 text-center'>Децентрализованный чат</h1>
        <SignIn/>
        <MessageList companionKey="AndrewPublicKey"/>
        <MessageInput recipientKey="AndrewPublicKey" recipientName="Andrew"/>
      </Container>
  );
}

export default App;
