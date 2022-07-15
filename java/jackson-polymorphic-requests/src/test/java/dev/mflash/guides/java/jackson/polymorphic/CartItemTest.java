package dev.mflash.guides.java.jackson.polymorphic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CartItemTest {

	private static final ObjectMapper mapper = new ObjectMapper();

	@Test
	@DisplayName("Should parse item as Software")
	void shouldParseItemAsSoftware() throws JsonProcessingException {
		final var item = """
				{
					"itemCategory": "SOFTWARE",
					"os": "Windows",
					"manufacturer": "Microsoft Software",
					"title": "Office Professional",
					"price": 6300.0,
					"version": "2021"
				}
				""";
		final var cartItem = mapper.readValue(item, CartItem.class);
		assertThat(cartItem)
				.isExactlyInstanceOf(Software.class)
				.satisfies(software -> assertThat(software.itemCategory()).isEqualTo(ItemCategory.SOFTWARE));
	}

	@Test
	@DisplayName("Should parse item as Accessory")
	void shouldParseItemAsAccessory() throws JsonProcessingException {
		final var item = """
				{
					"itemCategory": "ACCESSORY",
					"brand": "Dell",
					"manufacturer": "Dell Incorporation",
					"model": "MS116-XY",
					"price": 449.0,
					"specialFeatures": ["Wired", "Optical"]
				}
				""";
		final var cartItem = mapper.readValue(item, CartItem.class);
		assertThat(cartItem)
				.isExactlyInstanceOf(Accessory.class)
				.satisfies(accessory -> assertThat(accessory.itemCategory()).isEqualTo(ItemCategory.ACCESSORY));
	}

	@Test
	@DisplayName("Should throw exception on unknown item")
	void shouldThrowExceptionOnUnknownItem() {
		final var item = """
				{
					"itemCategory": "SNACK",
					"speciality": "Vegetarian",
					"form": "Toffee"
				}
				""";
		final Exception exception = catchException(() -> mapper.readValue(item, CartItem.class));
		assertThat(exception)
				.isExactlyInstanceOf(InvalidTypeIdException.class)
				.hasMessageStartingWith("Could not resolve type id 'SNACK'");
	}
}
