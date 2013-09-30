## About

Weather site that processes natural language requests and returns relevant data in a human-readable format.

This application is still rough around the edges. I coded the natural language processor and tokenizer from scratch.

### Roadmap:

- Improve natural language processing
- Improve data handling and missing data 
- Improve data formatting and response time
- 

## Technology Used

First and foremost, [Compojure](https://github.com/weavejester/compojure) was such a joy to use.

### Other dependencies 
- [Hiccup](https://github.com/weavejester/hiccup) for templateing
- [Clj-time](https://github.com/clj-time/clj-time) for easy time handling
- [Cheshire](https://github.com/dakrone/cheshire) for working with JSON
- [Forcast Clojure](https://github.com/jdhollis/forecast-clojure) for interfacing with Forcast.io
