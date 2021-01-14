import React, {useState} from 'react'
import "../pages/IssueDetail.css";
import "bootstrap/dist/css/bootstrap.css";

function IssueDetail() {
    const [comments, setComment] = useState([
        
        
    ]);
    const [newComment, addNewComment] = useState('');

    const updateValue = (event) => {
        addNewComment(event.target.value);

    };

    const Comment = ({comment}) => (
        
        <li>{comment.action}</li>
       

    );

        
    function newCommentItem() {
        setComment([...comments, {action: newComment, done:false}]);

    }


    return (
       

        <div>
            <div>
                <div className="comment">
                    <ul>
                        <li>                            
                            {comments.map((comment, index) => (
                            <Comment key={index} index={index} comment={comment} />
                                  
                            ))}                        
                        </li>
                    </ul>
                </div>

            
            
                <div className="comment-box">
                    <textarea 
                        value={newComment} 
                        onChange={updateValue} 
                        className="form-control" 
                        placeholder="Leave a comment"
                    ></textarea> 
                    
                    <button onClick={newCommentItem} className="btn btn-success mt-5 ml-3">Comment</button>
                </div>
            </div>
       
            
        </div>
    )
}

export default IssueDetail;
