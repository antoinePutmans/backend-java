package be.vinci.domain;

public class DomainFactoryImpl implements DomainFactory {
    private NewsImpl news;

    @Override
    public Page getPage() {
        return new PageImpl();
    }

    @Override
    public User getUser() {
        return new UserImpl();
    }

    @Override
    public News getNews() {
        return news;
    }
}
