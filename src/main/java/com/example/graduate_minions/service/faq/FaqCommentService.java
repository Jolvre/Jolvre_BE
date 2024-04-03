package com.example.graduate_minions.service.faq;

import com.example.graduate_minions.domain.faq.FaqComment;

public interface FaqCommentService {
    public FaqComment upload();

    public FaqComment update();
    
    public FaqComment delete();
}
