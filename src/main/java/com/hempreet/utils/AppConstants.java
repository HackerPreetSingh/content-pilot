package com.hempreet.utils;

import java.util.List;

public interface AppConstants {
    List<String> topics = List.of(
            "Java",
            "Spring Boot",
            "Microservices",
            "Kafka",
            "System Design",
            "Distributed Systems",
            "AWS",
            "Database (SQL/NoSQL)",
            "Performance Optimization",
            "Concurrency / Multithreading",
            "Design Patterns",
            "Debugging / Production Issues",
            "API Design",
            "Security (OAuth2/JWT)",
            "GenAI Integration"
    );

    List<String> formats = List.of(
            "Deep Dive",
            "Quick Tip",
            "Mistake / Pitfall",
            "Interview Question",
            "Real-world Scenario",
            "Comparison",
            "Step-by-step Guide",
            "Myth Busting",
            "Code Walkthrough",
            "Architecture Breakdown"
    );

    List<String> angles = List.of(
            "Most developers do this wrong",
            "This will break in production",
            "I learned this the hard way",
            "If you don’t know this, you’ll fail interviews",
            "This looks simple but isn’t",
            "Here’s what no one tells you",
            "Stop doing this in your code",
            "This one mistake costs hours of debugging",
            "You’re overengineering this",
            "This is why your system doesn’t scale"
    );
}
