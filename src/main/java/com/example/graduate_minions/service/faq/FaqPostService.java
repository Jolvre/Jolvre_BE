package com.example.graduate_minions.service.faq;

import com.example.graduate_minions.domain.faq.FaqPost;

public interface FaqPostService {
    public FaqPost upload();

    public FaqPost update();

    public FaqPost delete();
}
