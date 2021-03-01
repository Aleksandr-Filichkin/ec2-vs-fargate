package com.filichkin.blog.fargate.versus.ec2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class TestController {
    private final BookService bookService;
    private static final String BOOK = "{\"id\": \"95e5c253-bf91-43fb-bef3-86fadb95b615\", \"author\": \"Lucia Graves\", \"name\": \"The Shadow of the Wind\", \"country\": \"Spain\", \"locale\": \"en-us\", \"description\": \"Barcelona, 1945: A city slowly heals from its war wounds, and Daniel, an antiquarian book dealer's son who mourns the loss of his mother, finds solace in a mysterious book entitled The Shadow of the Wind, by one Julian Carax. But when he sets out to find the author's other works, he makes a shocking discovery: someone has been systematically destroying every copy of every book Carax has written. In fact, Daniel may have the last of Carax's books in existence. Soon Daniel's seemingly innocent quest opens a door into one of Barcelona's darkest secrets--an epic story of murder, madness, and doomed love.\", \"publishDate\": \"May 2001\", \"rating\": \"4.27\", \"lastFeedback\":[\"riveting. mysterious. haunting. imaginative. charming. sentimental.\\n\\nthe list of adjectives is endless. and whilst this book is all of these, the one thing that i will forever remember about this book is how it makes me appreciate the art of storytelling. i didnt feel like i was reading a novel; i felt as if someone very dear was sitting next to me and telling me their favourite tale. i was enamoured with the nuances of the language and swept up with all the action. it was an absolute pleasure to\", \"This is a book about books, a story about stories. It starts and ends in a library of sorts, themes and plots are echoed across decades, tied together by actors who find their roles changing, and by a pen that links two cycles of the story and has its own tale that started before and goes on beyond.\\n\\n\\\"the art of reading is slowly dying, it's an intimate ritual, a book is a mirror that offers us only what we already carry inside us, when we read, we do it with all our heart and mind, and great read\"]}\n";


    public TestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/fibonacci/{range}")
    public long fibonacci(@PathVariable int range) {
        int randomNum = ThreadLocalRandom.current().nextInt(range, range + 1000);
        long n1 = 0;
        long n2 = 1;
        long result = 0;
        for (int i = 2; i < randomNum; ++i) {
            result = n1 + n2;
            n1 = n2;
            n2 = result;
        }
        return result;
    }

    @PostMapping("/book")
    public Mono<String> save(@RequestBody String book) {
        return bookService.saveBook(String.valueOf(UUID.randomUUID().toString()), book);
    }

    @GetMapping("/book/{id}")
    public Mono<String> get(@PathVariable String id) {
        return bookService.getBook(id);
    }

    /**
     * Save hardcoded book
     *
     * @return book id
     */
    @PostMapping("/book-static")
    public Mono<String> save() {
        return bookService.saveBook(String.valueOf(UUID.randomUUID().toString()), BOOK);
    }

    /**
     * Save one hardcoded book and get {count} times this book from DB
     *
     * @param count number of parallel get requests after save
     * @return count
     */
    @PostMapping("/save-get/{count}")
    public Mono<Integer> saveMultiple(@PathVariable int count) {
        return save().flatMap(key ->
                Flux.merge(IntStream.range(0, count).boxed().map(x -> get(key)).collect(Collectors.toList()))
                        .collectList()
                        .map(list -> count));

    }

}
